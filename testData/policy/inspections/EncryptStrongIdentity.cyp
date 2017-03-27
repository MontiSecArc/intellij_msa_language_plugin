match (n:Instance)-[:OUTGOING]->(p1:Port)-[r:UNENCRYPTED]->(p2:Port)-[:INGOING]->(m:Instance)
  where (n)-[:WEAK|STRONG]->(m)
return r;