<script>
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";
import router from "@/router.js";
import ConfirmationPage from "@/components/Pages/ConfirmationPage.vue";
import axios from "axios";


export default {
  name: "RegistrationNavigation",
  methods: {
    useRegistrationStore,

    routerToNextSite() {

      if (useRegistrationStore().isDefaultDataFormCorrect() && this.isFirstPage) {
        useRegistrationStore().triedToValidateBasicForm = false;
        useRegistrationStore().triedToValidateFinancialForm = false;
        router.push("/kontodaten");
      } else if (useRegistrationStore().isFinancialFormCorrect() && this.isSecondPage) {
        useRegistrationStore().triedToValidateBasicForm = false;
        useRegistrationStore().triedToValidateFinancialForm = false;
        router.push("/zusammenfassung")
      }
    },

    routerToPastSite() {
      if (this.isSecondPage) {
        router.push("/");
      } else if (this.isLastPage) {
        router.push("/kontodaten")
      }
    },

    postFormData() {
      if (useRegistrationStore().isFinancialFormCorrect()) {
        this.postData();
      }
    },

    postData() {

      useRegistrationStore().updateFinancialValidation();
      if (!useRegistrationStore().registrationData.hiddenSecurityCheck) {

        useRegistrationStore().isLoadingRequest = true;

        let formData = new FormData();

        formData.append('formData', new Blob([JSON.stringify(useRegistrationStore().registrationData)], {type: 'application/json'}))

        useRegistrationStore().studentIdentification.forEach((file, index) => {
          formData.append(`studentIdentificationFiles`, file);
        });

        axios.post(useRegistrationStore().getTargetURL() + "/registrate", formData, {headers: {'Content-Type': 'multipart/form-data'}})
            .then(function (response) {
              useRegistrationStore().isLoadingRequest = false;
              router.push("/erfolg");
            })
            .catch(function (error) {

              if (error.status === 400) {
                useRegistrationStore().requestFailedWithWrongNonExistingEmail = true;
				useRegistrationStore().requestFailedWithError = error.message;
              }

              useRegistrationStore().isLoadingRequest = false;
              useRegistrationStore().requestFailed = true;
            });
      }
    },
  },

  data() {
    return {
      pages: [DefaultRegistration, FinanzialRegistration, ConfirmationPage],
      isLoading: true
    }
  },
  computed: {

    isFirstPage() {
      return this.$route.name === this.pages[0].name;
    },

    isSecondPage() {
      return this.$route.name === this.pages[1].name;
    },

    isLastPage() {
      return this.$route.name === this.pages[this.pages.length - 1].name;
    },

    getLastPageButtonText() {
      return this.$route.name === this.pages[this.pages.length - 1].name ? "Senden" : "Weiter";
    }
  }
}
</script>

<template>

  <div class="flex p-[1em]">

    <div :class="{ 'invisible': isFirstPage}" class="basis-1/3 ">

      <button @click="routerToPastSite" class="font-bold  px-[1em] py-[0.5em] rounded">
        Zurück
      </button>

    </div>

    <div class="flex basis-1/3 justify-center items-center gap-[1em]">
      <div v-for="page in pages" :class="{'!bg-white' : $route.name === page.name}"
           class="w-[0.8em] h-[0.8em] bg-stone-500 rounded-full transition-colors dot"></div>
    </div>

    <div class="basis-1/3 flex justify-end">
      <button v-if="!isLastPage" class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded" @click="routerToNextSite">
        {{ getLastPageButtonText }}
      </button>
      <button v-else class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded" @click="postFormData">
        <span v-if="!useRegistrationStore().isLoadingRequest">{{ getLastPageButtonText }}</span>
        <span v-else>
					<svg class="animate-spin bi bi-arrow-repeat" fill="currentColor" height="16" viewBox="0 0 16 16" width="16"
               xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41m-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9"/>
                        <path
                            d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5 5 0 0 0 8 3M3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9z"
                            fill-rule="evenodd"/>
					</svg>
				</span>
      </button>

    </div>

  </div>
</template>

<style scoped>

.dot {
  transition-property: color, background-color, border-color, text-decoration-color, fill, stroke;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

</style>