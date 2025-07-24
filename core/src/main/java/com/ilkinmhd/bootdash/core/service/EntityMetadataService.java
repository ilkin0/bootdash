package com.ilkinmhd.bootdash.core.service;

import com.ilkinmhd.bootdash.core.model.EntityMetadata;
import java.util.List;
import java.util.Optional;

/** Service for retrieving metadata about entities. */
public interface EntityMetadataService {
  /**
   * Get metadata for all registered entities.
   *
   * @return a list of entity metadata
   */
  List<EntityMetadata> getAllEntities();

  /**
   * Get metadata for a specific entity by name.
   *
   * @param entityName the name of the entity
   * @return an Optional containing the entity metadata, or empty if not found
   */
  Optional<EntityMetadata> getEntityMetadata(String entityName);

  /**
   * Get the distinct groups that entities belong to.
   *
   * @return a list of group names
   */
  List<String> getEntityGroups();

  /**
   * Scan for entities in the specified packages.
   *
   * @param basePackages the base packages to scan
   */
  void scanForEntities(String... basePackages);
}
