import {defineStore} from 'pinia'


export const useRegistrationStore = defineStore('registrationStore', {
    state: () => ({


        count: 0,
        name: 'Eduardo',





    }),
    getters: {
        doubleCount: (state) => state.count * 2,
    },
    actions: {
        increment() {
            this.count++
        },
    },
})