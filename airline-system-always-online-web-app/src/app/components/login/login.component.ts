import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Login } from '../../models/user/login';
import { UserService } from '../../services/user.service';
import { ResponseLoginDTO } from '../../dto/response-login-dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData: Login = new Login();

  responseLoginDTO: ResponseLoginDTO = new ResponseLoginDTO();

  loginForm = new FormGroup({

    email : new FormControl(),

    password : new FormControl()

  });

  public viewMicroserviceUser: boolean = false;

  public loading: boolean = false;

  refresh : boolean = false;

  viewPanel: boolean = false;

  viewError: boolean = false;

  deploymentVersion: string;

  // comunication to parent component
  @Output()
  loginUser: EventEmitter<ResponseLoginDTO> = new EventEmitter<ResponseLoginDTO>();

  // change to booking component
  @Output()
  changeBooking: EventEmitter<string> = new EventEmitter<string>();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  setComunication(loginUser: ResponseLoginDTO) {

    this.loginUser.emit(loginUser);

    this.changeBooking.emit('booking');

    console.log('set comnunication');

  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  send() {

    this.loginData.email = this.loginForm.value.email;

    this.loginData.password = this.loginForm.value.password;

    try {

      this.userService.login(this.loginData).toPromise().then(

        rest => {

          console.log('res: ' + JSON.stringify(rest));

          if(rest['errorCode'] === 0){

            this.responseLoginDTO = rest['data'];

            this.loading = false;

            this.viewError = false;

            this.setComunication(this.responseLoginDTO);

            console.log('Response', this.responseLoginDTO);

          }else {

            this.viewError = true;

            console.log('else');
          }
        }, error => {

            console.error('error: ' + error);

            this.loading = false;

            this.viewError = true;
        }

      );  
    } catch (error) {

      this.viewError = true;

    }

  }

  login() {
    this.send();
  }

}
