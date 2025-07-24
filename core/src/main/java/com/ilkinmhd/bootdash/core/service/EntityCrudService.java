package com.ilkinmhd.bootdash.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Service for performing CRUD operations on entities. */
public interface EntityCrudService {
  /**
   * Get a page of entities.
   *
   * @param entityName the name of the entity
   * @param page the page number (0-based)
   * @param size the page size
   * @param sortField the field to sort by
   * @param sortDirection the sort direction ("asc" or "desc")
   * @param filters a map of field names to filter values
   * @return a list of entity objects
   */
  List<Object> getEntities(
      String entityName,
      int page,
      int size,
      String sortField,
      String sortDirection,
      Map<String, Object> filters);

  /**
   * Count the total number of entities matching the filters.
   *
   * @param entityName the name of the entity
   * @param filters a map of field names to filter values
   * @return the total count
   */
  long countEntities(String entityName, Map<String, Object> filters);

  /**
   * Get a single entity by ID.
   *
   * @param entityName the name of the entity
   * @param id the ID of the entity
   * @return an Optional containing the entity, or empty if not found
   */
  Optional<Object> getEntity(String entityName, Object id);

  /**
   * Create a new entity.
   *
   * @param entityName the name of the entity
   * @param entityData a map of field names to values
   * @return the created entity
   */
  Object createEntity(String entityName, Map<String, Object> entityData);

  /**
   * Update an existing entity.
   *
   * @param entityName the name of the entity
   * @param id the ID of the entity
   * @param entityData a map of field names to values
   * @return the updated entity
   */
  Object updateEntity(String entityName, Object id, Map<String, Object> entityData);

  /**
   * Delete an entity.
   *
   * @param entityName the name of the entity
   * @param id the ID of the entity
   */
  void deleteEntity(String entityName, Object id);

  /**
   * Execute a custom action on an entity.
   *
   * @param entityName the name of the entity
   * @param actionName the name of the action
   * @param id the ID of the entity (may be null for non-individual actions)
   * @param parameters parameters for the action
   * @return the result of the action, if any
   */
  Object executeAction(
      String entityName, String actionName, Object id, Map<String, Object> parameters);
}
