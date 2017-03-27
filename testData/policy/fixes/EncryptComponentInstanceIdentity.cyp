match (n:Instance)-[:WEAK|STRONG]->(m:Instance)
match (n)-[:OUTGOING]->(p1:Port)-[r:UNENCRYPTED]->(p2:Port)-[:INGOING]->(m)
create (p1)-[r2:ENCRYPTED]->(p2)
set r2 = r
with r
delete r;