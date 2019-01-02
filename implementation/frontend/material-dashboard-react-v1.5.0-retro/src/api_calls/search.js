import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

class SearchRepository {

    static async search(endDateDD, endDateHH, endDateMM, endDateYYYY, listOfTags, location, startDateDD, startDateMM, startDateHH, startDateYYYY, text) {
      return axios.post(`${constants.API}/memory/filter`, {
        endDateDD: endDateDD,
        endDateHH: endDateHH,
        endDateMM: endDateMM,
        endDateYYYY: endDateYYYY,
        listOfTags: listOfTags,
        location: location,
        startDateDD: startDateDD,
        startDateMM: startDateMM,
        startDateHH: startDateHH,
        startDateYYYY: startDateYYYY,
        text: text
      });
    }
}

export default SearchRepository;
