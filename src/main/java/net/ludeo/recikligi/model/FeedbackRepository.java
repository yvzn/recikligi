package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {

}
