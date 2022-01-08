package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
    boolean existsByUserAndCourseId(UserModel user, UUID courseId);

    @Query(value = "select * from tb_users_courses where user_user_id = :userId", nativeQuery = true)
    List<UserCourseModel> findAllCourseIntoUser(@Param("userId") UUID userId );
}
