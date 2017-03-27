match (t1:Trustlevel)-[:TRUST]->(:Component)-[:DECLARES_OUT]->(n:Port)-[r:UNENCRYPTED]->(m:Port)-[:DECLARES_IN]->(:Component)-[:TRUST]->(t2:Trustlevel)
with t1.level as startTrustLevel,t2.level as stopTrustLevel,n,m,r
match (m)-[:INGOING]->(:Instance)-[:CHILD_OF|INSTANCE_OF]->(env:Component)-[:PARENT_OF|:DEFINES]->(:Instance)-[:OUTGOING]->(n)
with startTrustLevel, stopTrustLevel, r, env
match (env)-[:TRUST]->(t3:Trustlevel)
with startTrustLevel, stopTrustLevel, r, t3.level as envTrustLevel
  where envTrustLevel < startTrustLevel or envTrustLevel < stopTrustLevel
return r;