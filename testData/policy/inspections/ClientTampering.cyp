match (t1:Trustlevel)-[:TRUST]->()-[:DECLARES_OUT]->(n:Port)-[:UNENCRYPTED]->(m:Port)-[:DECLARES_IN]->()-[:TRUST]->(t2:Trustlevel)
where t1.level < t2.level
return m;