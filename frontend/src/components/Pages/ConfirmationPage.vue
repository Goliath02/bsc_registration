<script>
import FormHeader from "@/components/FormHeader.vue";
import {useRegistrationStore} from "../../stores/RegistrationStore.js";

export default {
  name: "ConfirmationPage",
  methods: {useRegistrationStore},
  components: {FormHeader}
}
</script>

<template>

  <FormHeader header-text="Zusammenfassung"/>
  <div class="flex w-full flex-col gap-[0.75em] p-[1em] max-h-[65vh] overflow-y-auto">

    <h2 class="font-bold text-xl">Personen Angaben</h2>

    <div class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap -lg:flex-col">

      <div class="flex flex-row gap-[0.8em] px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Name</div>
        <div>{{ useRegistrationStore().registrationData.mainData.name }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Nachname</div>
        <div>{{ useRegistrationStore().registrationData.mainData.surename }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Geschlecht</div>
        <div>{{ useRegistrationStore().registrationData.mainData.gender }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] py-[0.5em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>{{ useRegistrationStore().registrationData.mainData.birthday }}</div>
      </div>
    </div>

    <div v-for="person in useRegistrationStore().registrationData.morePersons"
         class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap -lg:flex-col ">

      <div class="flex flex-row gap-[1em] px-[1em] w-1/2">
        <div class="font-bold">Name</div>
        <div>{{ person.name }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] w-1/2">
        <div class="font-bold">Nachname</div>
        <div>{{ person.surename }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] w-1/2">
        <div class="font-bold">Geschlecht</div>
        <div>{{ person.gender }}</div>
      </div>

      <div class="flex flex-row gap-[1em]  px-[1em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>{{ person.birthday }}</div>
      </div>
    </div>

    <h2 class="font-bold text-xl">Weitere Angaben</h2>

    <div class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap -lg:flex-col ">

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Art der Anmeldung</div>
        <div>{{ useRegistrationStore().registrationData.mainData.type }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Grund</div>
        <div>{{ useRegistrationStore().registrationData.mainData.reason }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Straße</div>
        <div>{{ useRegistrationStore().registrationData.mainData.street }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Email</div>
        <div>{{ useRegistrationStore().registrationData.mainData.email }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Telefon/Mobil</div>
        <div>{{ useRegistrationStore().registrationData.mainData.phone }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Ort</div>
        <div>{{ useRegistrationStore().registrationData.mainData.place }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Postleitzahl</div>
        <div>{{ useRegistrationStore().registrationData.mainData.plz }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Geburtstag</div>
        <div>{{ useRegistrationStore().registrationData.mainData.birthday }}</div>
      </div>
    </div>

    <h2 class="font-bold text-xl">Kontodaten</h2>


    <div class="p-[1em] bg-[#141414] rounded-lg flex flex-wrap -lg:flex-col ">

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">IBAN</div>
        <div>{{ useRegistrationStore().registrationData.financial.iban }}</div>
      </div>


      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">BIC</div>
        <div>{{ useRegistrationStore().registrationData.financial.bic }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Name des Kontoinhabers</div>
        <div>{{ useRegistrationStore().registrationData.financial.nameOfBankOwner }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Nachname des Kontoinhabers</div>
        <div>{{ useRegistrationStore().registrationData.financial.sureNameBankOwner }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Name der Bank</div>
        <div>{{ useRegistrationStore().registrationData.financial.bankName }}</div>
      </div>

      <div class="flex flex-row gap-[1em] py-[0.5em] px-[1em] w-1/2">
        <div class="font-bold">Bank Ort</div>
        <div>{{ useRegistrationStore().registrationData.financial.bankPlace }}</div>
      </div>
    </div>


  </div>


</template>

<style scoped>

</style>



