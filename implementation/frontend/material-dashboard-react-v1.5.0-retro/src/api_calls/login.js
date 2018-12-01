import axios from "axios";
import Cookies from "js-cookie";

import constants from "../constants";

class LoginRepository {
  static login(loginNickname, loginEmail, loginPassword) {
    return axios.post(`${constants.API}/login`, {
      email: loginEmail,
      nickname: loginNickname,
      password: loginPassword
    }).then((response) => {
      const token = response.token;
      Cookies.set("retroToken", token);
    }).catch(console.log);
  }
  
// TODO implement once backend is ready.
  static forget(nickname, email, password) {
    return axios.post(`${constants.API}/forget`, {
      nickname: nickname,
      email: email,
      password: password
    });
  }

  static register(registerNickname, registerFirstName, registerLastName, registerEmail, registerPassword) {
      return axios.post(`${constants.API}/register`, {
        email: registerEmail,
        firstName: registerFirstName,
        lastName: registerLastName,
        nickname: registerNickname,
        password: registerPassword
      }).then((response) => {
        const token = response.token;
        Cookies.set("retroToken", token);
      }).catch(console.log);
  }
}

export default LoginRepository;
