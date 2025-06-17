/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.data.jpa.service.impl;

import hu.perit.ngface.core.types.intf.ComparisonOperator;
import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.Direction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericNgfaceQueryServiceImplTest
{

    // Test entity class
    @Getter
    @RequiredArgsConstructor
    static class TestEntity
    {
        private final Long id;
        private final String name;
        private final Integer age;
    }


    // Concrete implementation of GenericNgfaceQueryServiceImpl for testing
    static class TestQueryServiceImpl extends GenericNgfaceQueryServiceImpl<TestEntity, Long>
    {
        private final EntityManager entityManager;


        public TestQueryServiceImpl(GenericNgfaceQueryRepo<TestEntity, Long> repo, EntityManager entityManager)
        {
            super(repo, 10); // Default page size is 10
            this.entityManager = entityManager;
        }


        @Override
        protected List<Sort.Order> getDefaultSortOrder()
        {
            return List.of(new Sort.Order(Sort.Direction.ASC, "name"));
        }


        @Override
        protected EntityManager getEntityManager()
        {
            return entityManager;
        }
    }


    @Mock
    private GenericNgfaceQueryRepo<TestEntity, Long> mockRepo;

    @Mock
    private EntityManager mockEntityManager;

    private TestQueryServiceImpl queryService;


    @BeforeEach
    void setUp()
    {
        queryService = new TestQueryServiceImpl(mockRepo, mockEntityManager);
    }


    @Test
    void testFindWithNoFilters()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        List<TestEntity> entities = List.of(
                new TestEntity(1L, "Test 1", 25),
                new TestEntity(2L, "Test 2", 30)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        when(mockRepo.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        // Verify that findAll was called with the correct page request
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mockRepo).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(5);
    }


    @Test
    void testFindWithSorting()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));
        params.setSort(new DataRetrievalParams.Sort());
        params.getSort().setColumn("age");
        params.getSort().setDirection(Direction.DESC);

        List<TestEntity> entities = List.of(
                new TestEntity(2L, "Test 2", 30),
                new TestEntity(1L, "Test 1", 25)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        when(mockRepo.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        // Verify that findAll was called with the correct page request and sort
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mockRepo).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(5);
        assertThat(pageable.getSort().getOrderFor("age").getDirection()).isEqualTo(Sort.Direction.DESC);
    }


    @Test
    void testFindWithEqualityFilter()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        // Create a filter for name = "Test 1"
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn("name");
        filter.setOperator(ComparisonOperator.EQ);
        filter.setValueSet(List.of(new DataRetrievalParams.Filter.Item("Test 1")));

        params.setFilters(List.of(filter));

        List<TestEntity> entities = List.of(
                new TestEntity(1L, "Test 1", 25)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        // Mock the repository to return our test data
        when(mockRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test 1");

        // Verify that findAll was called with a specification and pageable
        verify(mockRepo).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void testFindWithGreaterThanFilter()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        // Create a filter for age > 25
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn("age");
        filter.setOperator(ComparisonOperator.GT);
        filter.setValueSet(List.of(new DataRetrievalParams.Filter.Item("25")));

        params.setFilters(List.of(filter));

        List<TestEntity> entities = List.of(
                new TestEntity(2L, "Test 2", 30)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        // Mock the repository to return our test data
        when(mockRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAge()).isEqualTo(30);

        // Verify that findAll was called with a specification and pageable
        verify(mockRepo).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void testFindWithLikeFilter()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        // Create a filter for name LIKE "Test"
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn("name");
        filter.setOperator(ComparisonOperator.LIKE);
        filter.setValueSet(List.of(new DataRetrievalParams.Filter.Item("Test")));

        params.setFilters(List.of(filter));

        List<TestEntity> entities = List.of(
                new TestEntity(1L, "Test 1", 25),
                new TestEntity(2L, "Test 2", 30)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        // Mock the repository to return our test data
        when(mockRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        // Verify that findAll was called with a specification and pageable
        verify(mockRepo).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void testFindWithBetweenFilter()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        // Create a filter for age BETWEEN 20 AND 30
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn("age");
        filter.setOperator(ComparisonOperator.BETWEEN);
        filter.setValueSet(List.of(
                new DataRetrievalParams.Filter.Item("20"),
                new DataRetrievalParams.Filter.Item("30")
        ));

        params.setFilters(List.of(filter));

        List<TestEntity> entities = List.of(
                new TestEntity(1L, "Test 1", 25),
                new TestEntity(2L, "Test 2", 30)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        // Mock the repository to return our test data
        when(mockRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        // Verify that findAll was called with a specification and pageable
        verify(mockRepo).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void testFindWithInFilter()
    {
        // Arrange
        DataRetrievalParams params = new DataRetrievalParams();
        params.setPage(new DataRetrievalParams.Page().index(0).size(5));

        // Create a filter for name IN ("Test 1", "Test 2")
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn("name");
        filter.setOperator(ComparisonOperator.IN);
        filter.setValueSet(List.of(
                new DataRetrievalParams.Filter.Item("Test 1"),
                new DataRetrievalParams.Filter.Item("Test 2")
        ));

        params.setFilters(List.of(filter));

        List<TestEntity> entities = List.of(
                new TestEntity(1L, "Test 1", 25),
                new TestEntity(2L, "Test 2", 30)
        );
        Page<TestEntity> expectedPage = new PageImpl<>(entities);

        // Mock the repository to return our test data
        when(mockRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<TestEntity> result = queryService.find(params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        // Verify that findAll was called with a specification and pageable
        verify(mockRepo).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void testGetDistinctValues()
    {
        // Arrange
        String fieldName = "name";
        String searchText = "Test";
        List<String> expectedValues = List.of("Test 1", "Test 2");

        // Mock the EntityManager and related objects
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<String> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<TestEntity> mockRoot = mock(Root.class);
        @SuppressWarnings("unchecked")
        Path<Object> mockPath = mock(Path.class);
        Predicate mockPredicate = mock(Predicate.class);

        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(String.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(TestEntity.class)).thenReturn(mockRoot);
        when(mockCriteriaQuery.distinct(true)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.select(any())).thenReturn(mockCriteriaQuery);
        when(mockRoot.get(fieldName)).thenReturn(mockPath);
        when(mockCriteriaBuilder.conjunction()).thenReturn(mockPredicate);
        when(mockCriteriaBuilder.like(any(), anyString())).thenReturn(mockPredicate);
        // Skip mocking the lower method as it's causing type issues
        when(mockCriteriaBuilder.and(any(), any())).thenReturn(mockPredicate);
        when(mockCriteriaQuery.where(any(Predicate.class))).thenReturn(mockCriteriaQuery);

        @SuppressWarnings("unchecked")
        jakarta.persistence.TypedQuery<String> mockTypedQuery = mock(jakarta.persistence.TypedQuery.class);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(expectedValues);

        // Act
        List<String> result = queryService.getDistinctValues(fieldName, searchText, TestEntity.class, null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedValues);
    }


    @Test
    void testFindAllByIds()
    {
        // Arrange
        String idFieldName = "id";
        List<Long> ids = List.of(1L, 2L);

        List<TestEntity> expectedEntities = List.of(
                new TestEntity(1L, "Test 1", 25),
                new TestEntity(2L, "Test 2", 30)
        );

        when(mockRepo.findAll(any(Specification.class))).thenReturn(expectedEntities);

        // Act
        List<TestEntity> result = queryService.findAllByIds(idFieldName, ids);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        // Verify that findAll was called with a specification
        verify(mockRepo).findAll(any(Specification.class));
    }
}
