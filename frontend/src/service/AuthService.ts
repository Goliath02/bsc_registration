import axios from 'axios'

const api = axios.create({
  baseURL: '/api', // anpassen
  withCredentials: true, // falls Session-Cookie
})

export async function loginWithCredentials(email: string, password: string) {
  const res = await api.post('/auth/login', {"email": email,
    "password": password })
  return res.data // z. B. { token: "JWT-xyz" }
}
