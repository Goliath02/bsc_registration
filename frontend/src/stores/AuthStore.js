import {defineStore} from 'pinia'
import router from "@/router.js";
import {apiClient} from "@/apiClient.js";

export const useAuthStore = defineStore('authStore', {
    state: () => ({
        loading: null,
        error: null,
        username: null
    }),

    actions: {

        async login(email, password) {
            this.loading = true;
            this.error = null
            try {
                await apiClient.post('/api/auth/login', {
                    "email": email,
                    "password": password
                }, {
                    withCredentials: true
                });

                // Nach erfolgreichem Login – redirect
                router.push('/dashboard');
            } catch (error) {
                return Promise.reject(error);
            }
            finally {
                this.loading = false
            }
        },

        logout() {
            this.user = null
            this.token = null
            localStorage.removeItem('token')
        },



        getTargetURL() {
            return import.meta.env.DEV ? "http://localhost:8080" : "";
        }
    }
})