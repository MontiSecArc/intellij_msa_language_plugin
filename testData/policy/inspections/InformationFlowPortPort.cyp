MATCH (p1:Port)-->(p2:Port)
  WHERE (p1)-[:SECURITY_CLASS|WEAKER*2..8]->(:SecurityClass)<--(p2)
RETURN p2;
