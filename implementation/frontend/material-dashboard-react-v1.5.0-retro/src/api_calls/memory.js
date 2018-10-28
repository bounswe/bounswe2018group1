import axios from "axios";

import constants from "../constants";

class MemoryRepository {


  createMemory() {
    static createMemory(description, headline, storyList) {
      return axios.post(`${constants.API}/memory`, {
        description: description,
        headline: headline,
        storyList: JSON.stringify(storyList)
      });
    }
  }

  getMemory() {
    static getMemory(id) {
      return axios.get(`${constants.API}/memory`, {
        id: id
      });
    }
  }

  deleteMemory() {

  }
}

export default MemoryRepository;
