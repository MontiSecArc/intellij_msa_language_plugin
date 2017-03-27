MATCH (n)-[:OUTGOING]->(:Port)
        -[r:UNENCRYPTED]->(:Port)
        -[:INGOING]->(m)
        WHERE (n:Instance)-[:WEAK|STRONG]->(m:Instance)
RETURN r;
