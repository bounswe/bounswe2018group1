import axios from "axios";
import Cookies from "js-cookie";

import constants from "../constants";

class LoginRepository {
  static login(loginNickname, loginEmail, loginPassword) {
    return axios.post(`${constants.API}/login`, {
      nickname: loginNickname,
      email: loginEmail,
      password: loginPassword
    }).then((response) => {
      const token = response.token;
      Cookies.set("retroToken", token);
    });
  }

  static forget(nickname, email, password) {
    return axios.post(`${constants.API}/forget`, {
      nickname: nickname,
      email: email,
      password: password
    });
  }

  static register(registerNickname, registerEmail, registerPassword) {
    return axios.post(`${constants.API}/register`, {
      nickname: registerNickname,
      email: registerEmail,
      password: registerPassword
    });
  }
}

export default LoginRepository;
