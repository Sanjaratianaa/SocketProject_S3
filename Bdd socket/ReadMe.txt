Pour creer une base:
>> create database <nomBase>

Pour creer une table:
>> create table <nomTable> in <nomBase>

Pour inserer des donnees:
>> insert into <nomTable> values [...,...,...] in <nomBase>

Pour une select normale:
>> select * from <nomTable> in <nomBase>

Pour union et intersection
>> select <nomOperation>[<nomTable1>/<nomTable2>] in <nomBase>

Pour produitCartesien et difference
>> select <nomOperation>[<nomTable1>/<nomTable2>] in <nomBase>

Pour sous-requete
>> select * from [ select * from <nomTable> in <nomBase> ]
>> select * from [ select * from [ select * from <nomTable> in <nomBase> ] ]

Pour condition
>> select * from <nomTable> where [<nomColonne>=<value>/<nomColonne>=<value>] in <nomBase>

Pour join
>> select * from <nomTable> join <nomTable> on <nomCol> in <nomBase>


### pour changer le repertoire, c'est dans ServerThread.java
Pour lancer l'application:
    . Tout compiler avec "c.bat"
    . Lancer le serveur avec "serveur.bat"
    . Lancer le client avec "client.bat"


