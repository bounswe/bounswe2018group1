import axios from "axios";

import constants from "../constants";

class LoginRepository {
  static login(username, email, password) {
    return axios.post(`${constants.API}/login`, {
      email,
      username,
      password
    });
  }

  static register(username, email, password) {

  }
}

export default LoginRepository;
