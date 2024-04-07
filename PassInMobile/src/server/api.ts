import axios from "axios";

export const api = axios.create({
  baseURL: "http://[[API_HOST]]:[[API_PORT]]",
});
