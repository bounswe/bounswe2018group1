import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.withCredentials = true;

class MediaRepository {

    static async upload(media) {
      return axios.post(`${constants.API}/media`, {
        file: media
      });
    }

    static async delete(url) {
      return axios.delete(`${constants.API}/media`, {
        fileUrl: url
      });
    }

}

export default MediaRepository;
