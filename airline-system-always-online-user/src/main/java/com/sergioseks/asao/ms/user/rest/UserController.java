package com.sergioseks.asao.ms.user.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergioseks.asao.ms.user.dto.LoginDTO;
import com.sergioseks.asao.ms.user.dto.ResponseDTO;
import com.sergioseks.asao.ms.user.dto.ResponseUserDTO;
import com.sergioseks.asao.ms.user.dto.UserDTO;
import com.sergioseks.asao.ms.user.models.Document;
import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.models.User;
import com.sergioseks.asao.ms.user.services.implement.DocumentService;
import com.sergioseks.asao.ms.user.services.implement.PhoneService;
import com.sergioseks.asao.ms.user.services.implement.UserService;
import com.sergioseks.asao.ms.user.uitls.ServerDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private PhoneService phoneService;

	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;

	@ApiOperation(notes = "Return all users", value = "users", nickname = "users")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthUsers() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		List<User> users = new ArrayList<>();

		try {

			users = userService.findAll();

			response.setErrorCode(0);
			response.setData(users);
			response.setErrorMessage("Successfully operation: fetch all users");
			response.setHttpCode("" + HttpStatus.OK.value());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			response.setErrorCode(1);
			response.setErrorMessage(e.getLocalizedMessage());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return save user", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> saveUser(@RequestBody User user) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			User userData = userService.save(user);

			responseDTO.setErrorCode(0);
			responseDTO.setData(userData);
			responseDTO.setErrorMessage("Successfully operation: save user");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return update user", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> updateUser(@RequestBody User user) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			User userData = userService.save(user);

			responseDTO.setErrorCode(0);
			responseDTO.setData(userData);
			responseDTO.setErrorMessage("Successfully operation: update user");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Return user login", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseUserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO login) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			ResponseUserDTO responseUserDTO = new ResponseUserDTO();
			
			User userData = userService.login(login.getEmail(), login.getPassword());
			
			if (userData != null) {
				
				// user data
				responseUserDTO.setId(userData.getId());
				responseUserDTO.setDateOfBirth(userData.getDateOfBirth());
				responseUserDTO.setFirtsName(userData.getFirtsName());
				responseUserDTO.setLastName(userData.getLastName());
				responseUserDTO.setEmail(login.getEmail());
				responseUserDTO.setPassword(login.getPassword());
				
				// user documents
				List<Document> documents = documentService.documentByUser(userData);
				
				// user phones
				List<Phone> phones = phoneService.phoneByUser(userData);
				
				responseUserDTO.setDocuments(documents);
				responseUserDTO.setPhones(phones);
				
			}
			
			responseDTO.setErrorCode(0);
			responseDTO.setData(responseUserDTO);
			responseDTO.setErrorMessage("Successfully operation: login user");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return user by id", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthUserById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			Optional<User> user = userService.findById(id);

			if (user.isPresent()) {

				response.setErrorCode(0);
				response.setData(user);
				response.setErrorMessage("Successfully operation: fetch user by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(user);
				response.setErrorMessage("Successfully operation: not found user");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			}

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			response.setErrorCode(1);
			response.setErrorMessage(e.getLocalizedMessage());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return consolidated user by id", value = "users", nickname = "users")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{user_id}/documents/{document_id}/phones/{phone_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthConsolidatedUserById(@PathVariable("user_id") int user_id,
			@PathVariable("document_id") int document_id, @PathVariable("phone_id") int phone_id)
			throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		UserDTO userDTO = new UserDTO();

		try {

			Optional<User> user = userService.findById(user_id);

			List<Document> documents = documentService.documentByUser(user.get());
			
			List<Phone> phones = phoneService.phoneByUser(user.get());
			
			if (user.isPresent() && !documents.isEmpty() && !phones.isEmpty()) {

				// user data
				userDTO.setDateOfBirth(user.get().getDateOfBirth());
				userDTO.setFirtsName(user.get().getFirtsName());
				userDTO.setLastName(user.get().getLastName());

				// documents data by user
				for (int i = 0; i < documents.size(); i++) {
					
					Document doc = documents.get(i);
					
					if (doc.getId() == document_id) {
						
						userDTO.setDocumentType(doc.getDocumentType());
						userDTO.setNumberDocument(doc.getNumber());
						userDTO.setExpiryDate(doc.getExpiryDate());
						userDTO.setIssuanceCountry(doc.getIssuanceCountry());
						userDTO.setNationality(doc.getNationality());
						userDTO.setHolder(doc.isHolder());
						
						break;
					}
				}
				
				for (int i = 0; i < phones.size(); i++) {
					
					Phone phone = phones.get(i);
					
					if (phone.getId() == phone_id) {
						
						userDTO.setCountryCallingCode(phone.getCountryCallingCode());
						userDTO.setNumberPhone(phone.getNumber());
						userDTO.setDeviceType(phone.getDeviceType());
						
						break;
					}
				}

			}

			response.setErrorCode(0);
			response.setData(userDTO);
			response.setErrorMessage("Successfully operation: fetch consolidated user by id");
			response.setHttpCode("" + HttpStatus.OK.value());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			response.setErrorCode(1);
			response.setErrorMessage(e.getLocalizedMessage());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Delete user by id", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			userService.deleteById(id);

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete user by id");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Delete all users", value = "user", nickname = "user")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = User.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteUsers() throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			userService.deleteAll();

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete all users");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
