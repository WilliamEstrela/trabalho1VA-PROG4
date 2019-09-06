package util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	public static void findClasses(Visitor<String> visitor, URLClassLoader cl) {
		URL[] urls=cl.getURLs();
		String[] paths = new String[urls.length];
		for (int i = 0; i < urls.length; i++) {
			URL url = urls[i];
			paths[i]=url.getFile();
		}
		findClass(visitor,paths);
		
	}
    public static void findClasses(Visitor<String> visitor) {
        String classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(System.getProperty("path.separator"));

        findClass(visitor, paths);
    }
	private static void findClass(Visitor<String> visitor, String[] paths) {
		File file=null;
		/*String javaHome = System.getProperty("java.home");
        File file = new File(javaHome + File.separator + "lib");
        if (file.exists()) {
            findClasses(file, file, true, visitor);
        }*/

        for (String path : paths) {
        	path=path.replaceAll("%20", " ");
            file = new File(path);
            if (file.exists()) {
                findClasses(file, file, true, visitor);
            }
        }
	}
    public static void main(String[] args) {
		ClassFinder.findClasses(new Visitor<String>() {
			
			@Override
			public boolean visit(String t) {
				System.out.println(t);
				return true;
			}
		});
	}

    private static boolean findClasses(File root, File file, boolean includeJars, Visitor<String> visitor) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!findClasses(root, child, includeJars, visitor)) {
                    return false;
                }
            }
        } else {
            if (file.getName().toLowerCase().endsWith(".jar") && includeJars) {
                JarFile jar = null;
                try {
                    jar = new JarFile(file);
                } catch (Exception ex) {

                }
                if (jar != null) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        int extIndex = name.lastIndexOf(".class");
                        if (extIndex > 0) {
                            if (!visitor.visit(name.substring(0, extIndex).replace("/", "."))) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if (file.getName().toLowerCase().endsWith(".class")) {
                if (!visitor.visit(createClassName(root, file))) {
                    return false;
                }
            }
        }

        return true;
    }

    private static String createClassName(File root, File file) {
        StringBuffer sb = new StringBuffer();
        String fileName = file.getName();
        sb.append(fileName.substring(0, fileName.lastIndexOf(".class")));
        file = file.getParentFile();
        while (file != null && !file.equals(root)) {
            sb.insert(0, '.').insert(0, file.getName());
            file = file.getParentFile();
        }
        return sb.toString();
    }
}