import axios from "axios";

const api = axios.create({
  withCredentials: true, // falls Session-Cookie
})

//TODO configure proxy

export async function loginWithCredentials(email: string, password: string) {
  const res = await api.post('/auth/login', {"email": email,
    "password": password })
  return res.data // z. B. { token: "JWT-xyz" }
}
