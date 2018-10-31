import axios from "axios";

import constants from "../constants";

class MemoryRepository {

    static createMemory(description, headline, storyList) {
      return axios.post(`${constants.API}/memory`, {
        description: description,
        headline: headline,
        storyList: JSON.stringify(storyList)
      });
    }

    static getMemory(id) {
      return axios.get(`${constants.API}/memory`, {
        id: id
      });
    }

    static deleteMemory() {

    }
}

export default MemoryRepository;
