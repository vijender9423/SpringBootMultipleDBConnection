package com.shl.scoring.amcat.repositories.tc;

import com.shl.scoring.amcat.models.tc.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<user,Long>
{
}
