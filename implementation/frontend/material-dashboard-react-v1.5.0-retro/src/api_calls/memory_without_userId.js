import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.withCredentials = true;

class MemoryRepositoryWithoutUser {
    static async getMemory(memoryID) {
      const response = await axios(
        {
          method: 'GET',
          url: `${constants.API}/memory`,
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
          },
          params:{
            id: 47
          }
        }
      )
      return response.data;
    }

    static async getAllMemories() {
      const response = await axios(
        {
          method: 'GET',
          url: `${constants.API}/memory/all`,
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          }
        }
      )
      return response.data;
    }
}

export default MemoryRepositoryWithoutUser;
