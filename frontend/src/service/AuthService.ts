import axios from "axios";

// does not work for some reason?

export async function loginWithCredentials(email: string, password: string) {
  const res = await axios.post(
    "/api/auth/login",
    {
      email: email,
      password: password,
    },
    {
      withCredentials: true,
    },
  );
  return res.data; // z. B. { token: "JWT-xyz" }
}
