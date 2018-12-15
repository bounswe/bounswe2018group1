import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

//Change once implemented
class SearchRepository {

    static async search(type, query) {
      return axios.get(`${constants.API}/search`, {
        type: type,
        text: query
      });
    }
}

export default SearchRepository;
