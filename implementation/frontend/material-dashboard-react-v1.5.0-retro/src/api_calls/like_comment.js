import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

//Change once implemented
class Like_CommentRepository {

    static async comment(comment, memoryId) {
      return axios.post(`${constants.API}/memory/comment`, {
        comment: comment,
        memoryId: memoryId
      });
    }

    static async deleteComment(commentId) {
      return axios.delete(`${constants.API}/memory/comment?commentId=${commentId}`);
    }

    static async getComment(commentId) {
      return axios.get(`${constants.API}/memory/comment`, {
        commentId: commentId
      });
    }

    static async getLikes(memoryId) {
      return axios.get(`${constants.API}/memory/like`, {
        memoryId: memoryId
      });
    }

    static async like(owner, memoryId) {
      return axios.post(`${constants.API}/memory/like`, {
        owner: owner,
        memoryId: memoryId
      });
    }

    static async unlike(owner, memoryId) {
      return axios.post(`${constants.API}/memory/like`, {
        owner: owner,
        memoryId: memoryId
      });
    }
}

export default Like_CommentRepository;
