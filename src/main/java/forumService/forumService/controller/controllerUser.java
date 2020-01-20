package forumService.forumService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.models.user;
import forumService.forumService.repository.userRepository;

@RestController
@RequestMapping(name = "userService")
public class controllerUser {
	@Autowired
	userRepository repo;

	@PutMapping(name = "/save")
	public void save(@RequestBody user u) {
		repo.save(u);
	}
}
