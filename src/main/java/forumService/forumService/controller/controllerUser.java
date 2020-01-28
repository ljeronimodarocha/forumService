package forumService.forumService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.models.user;
import forumService.forumService.repository.userRepository;

@RestController
@CrossOrigin
public class controllerUser {
	@Autowired
	userRepository repo;

	@RequestMapping(method = RequestMethod.POST, value = "save")
	public void save(@RequestBody user u) {
		System.out.println("teste save");
		repo.save(u);
	}

	@RequestMapping(method = RequestMethod.GET, value = "get")
	public List<user> get() {
		System.out.println("teste");
		return repo.findAll();
	}

	@GetMapping(path = "/checkEmail/{email}")
	public user checkEmail(@PathVariable String email) {
		System.out.println("metodo checkEmail");
		System.out.println(email);

		return repo.findByEmail(email);
		
	}

}
