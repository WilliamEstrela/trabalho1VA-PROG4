select vlan,nome 	from vlan where vlan	=1
select idtipo,tipo 	from tipo where idtipo	=1

insert into vlan(vlan,nome) 	values(1,'nome de teste')
insert into tipo(idtipo,tipo) 	values(1,'nome do tipo')

update vlan set vlan=1, nome='nome de teste'  where vlan=1
update tipo set idtipo=1, tipo='nome do tipo' where idtipo=1

delete from vlan where vlan=1
delete from tipo where idtipo=1