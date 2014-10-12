package com.zavakid.springtide.example.todolist.repository

import com.zavakid.springtide.example.todolist.model.Task
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}
import org.springframework.stereotype.Repository

/**
 *
 * @author zebin.xuzb 2014-09-27
 */
@Repository
trait TaskRepository extends JpaRepository[Task, java.lang.Long] with JpaSpecificationExecutor[Task] {

}
