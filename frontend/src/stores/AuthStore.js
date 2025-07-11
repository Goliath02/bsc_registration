import {defineStore} from 'pinia'
import {RegistrationType} from "@/components/BasicRegistration/dto/RegistrationType.js";
import {AgeType} from "@/components/BasicRegistration/dto/AgeType.js";
import * as dateUtil from "@/utils/dateUtil.js";
import axios from "axios";
import router from "@/router.js";

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
                await axios.post('http://localhost:8080/api/auth/login', {
                    "email": email,
                    "password": password
                }, {
                    withCredentials: true
                });

                // Nach erfolgreichem Login – redirect
                router.push('/dashboard');
            } catch (error) {
                console.error('Login fehlgeschlagen', error);
                alert('Login fehlgeschlagen!');
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