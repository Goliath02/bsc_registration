<script>
import { useRegistrationStore } from "@/stores/RegistrationStore.js";

export default {
  name: "ResponseModal",
  methods: {
    useRegistrationStore,

    setErrorBack() {
      useRegistrationStore().requestFailedWithError = "";
      useRegistrationStore().requestFailed = false;
    },
  },
};
</script>

<template>
  <div
    @click="setErrorBack"
    class="fixed top-0 bottom-0 left-0 right-0 modalBackground flex justify-center items-center"
  >
    <div
      v-if="useRegistrationStore().requestFailedWithError === 'EMAIL_NOT_FOUND'"
      class="lg:w-1/4 flex flex-col justify-center bg-bsc-lightgray rounded-lg text-white p-[1em] shadow-lg shadow-black"
    >
      <h1 class="text-2xl font-bold mb-2">
        Wir konnten keine Email mit der Adresse
        {{ useRegistrationStore().registrationData.mainData.email }} erreichen
      </h1>

      <div>
        <p>Bitte überprüfen Sie Ihre Email und versuchen es erneut.</p>
        <p>
          Wenn diese Email doch aktiv ist und stimmen sollte kontaktieren Sie
          bitte
          <a class="text-blue-500" href="mailto: krueger@1bsc.info"
            >krueger@1bsc.info</a
          >
        </p>
      </div>
    </div>

    <div
      v-else-if="
        useRegistrationStore().requestFailedWithError === 'IMAGE_TOO_LARGE'
      "
      class="lg:w-1/4 flex flex-col justify-center bg-bsc-lightgray rounded-lg text-white p-[1em] shadow-lg shadow-black"
    >
      <h1 class="text-2xl font-bold mb-2">Ihr Schülernachweis ist zu groß.</h1>

      <div>
        <p>Es sind maximal 8MB erlaubt</p>
      </div>
    </div>

    <div
      v-else
      class="lg:w-1/4 flex flex-col justify-center bg-bsc-lightgray rounded-lg text-white p-[1em] shadow-lg shadow-black"
    >
      <h1 class="text-2xl font-bold mb-2">Etwas is schief gelaufen :/</h1>

      <div>
        <p>Bitte versuchen Sie es später erneut.</p>
        <p>
          Bitte kontaktieren Sie
          <a class="text-blue-500" href="mailto: krueger@1bsc.info"
            >krueger@1bsc.info</a
          >
          um uns weiterzuhelfen was schief gelaufen ist.
        </p>
        <p>Wir versuchen das Problem in nächster Zeit zu beheben.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modalBackground {
  background-color: rgba(0, 0, 0, 0.3);
}
</style>
