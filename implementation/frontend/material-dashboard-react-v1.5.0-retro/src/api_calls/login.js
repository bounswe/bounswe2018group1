import axios from "axios";

import constants from "../constants";

class LoginRepository {
  static login(nickname, email, password) {
    return axios.post(`${constants.API}/login`, {
      nickname: nickname,
      email: email,
      password: password
    });
  }

  static register(nickname, email, password) {
    return axios.post(`${constants.API}/register`, {
      nickname: nickname,
      email: email,
      password: password
    });
  }
}

export default LoginRepository;
