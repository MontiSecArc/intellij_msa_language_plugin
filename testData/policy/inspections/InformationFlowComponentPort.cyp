MATCH (c:Component)-[:DECLARES_OUT]->(p:Port)
  WHERE (c)-[:WEAKER|CLEARANCE_FOR*2..8]->(:SecurityClass)<--(p)
RETURN c;
