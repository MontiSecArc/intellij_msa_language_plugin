match (c:Component)
with (:Component)-->(:Instance)-->(:Port)-->(:Port)-->(:Instance)-->(c) as path, c
where size(path) > 1 and not c.name starts with "Access"
with extract(x in path | head(nodes(x))) as StartComponents, c, path
unwind StartComponents as sc
with count(distinct sc) as DistinctStartComponents, c, path
where DistinctStartComponents > 1
create (accessPoint:Component {name: "AccessPoint" + c.name, access_roles:[]})
create (accessPointInstance:Instance {name: "accessPoint" + c.name})
create (accessPoint)-[:INSTANTIATION_OF]->(accessPointInstance)-[:INSTANCE_OF]->(accessPoint)
with accessPoint, c, accessPointInstance
match p = (p1:Port)-[r]->(p2:Port)-->(:Instance)-->(c)
create (p3:Port {name: "out" + p2.name})-[r2:UNENCRYPTED]->(p2)
create (p1)-[r3:UNENCRYPTED]->(p4:Port {name: "in" + p2.name})
create (accessPoint)-[:DECLARES_OUT]->(p3)<-[:OUTGOING]-(accessPointInstance)
create (accessPoint)<-[:DECLARES_IN]-(p4)-[:INGOING]->(accessPointInstance)
set r2 = r
set r3 = r
delete r
return accessPoint, accessPointInstance, p3, p4;