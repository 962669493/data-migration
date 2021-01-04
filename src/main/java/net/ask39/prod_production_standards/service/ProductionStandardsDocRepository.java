package net.ask39.prod_production_standards.service;

import net.ask39.prod_production_standards.entity.ProductionStandardsDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductionStandardsDocRepository extends MongoRepository<ProductionStandardsDoc, String> {
}
