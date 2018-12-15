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
      // const token = response.token;
      // Cookies.set("retroToken", token);
      const token = response.data.jsessionID;
      Cookies.set("JSESSIONID", token);
      console.log(response.data);
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

  // TODO implement once backend is ready.
    static activate(email) {
      return axios.post(`${constants.API}/forget`, {
        email: email
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
        // const token = response.token;
        // Cookies.set("retroToken", token);
      }).catch(console.log);
  }

  static async logout() {
    const token = Cookies.get("JSESSIONID");
    const response = await axios(
      {
        method: 'POST',
        url: `${constants.API}/logout`,
        withCredentials: true,
        crossdomain : true,
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
          // "Authorization": "bearer " + token
          // "Access-Control-Allow-Origin": 'http://localhost:1337',
          // "Access-Control-Allow-Methods": 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
          // 'Access-Control-Allow-Headers': "Content-Type"
          },
        }
      )
    Cookies.remove("JSESSIONID");
    return [];
  }
}

export default LoginRepository;
