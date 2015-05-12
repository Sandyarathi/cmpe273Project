package Facebook;

import FacebookUser.UPost;
import org.springframework.data.mongodb.repository.MongoRepository;

//@RepositoryRestResource(collectionResourceRel = "example", path = "api/v1/moderators")
interface PostRepository extends MongoRepository<UPost, String> {


}