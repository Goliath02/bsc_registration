<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import BscInput from "@/components/BasicRegistration/BSCInput.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import AddMemberButton from "@/components/BasicRegistration/AddMemberButton.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/ReworkedBSCInput.vue";
import ExtraPersonForm from "@/components/BasicRegistration/ExtraPersonForm.vue";


export default {
  name: "DefaultRegistration",
  components: {ReworkedBSCInput, ExtraPersonForm, AddMemberButton, GenderSelection, FormHeader, BscInput, GrundAuwahl, PeronenAuswahl},


  data() {
    return {
      mainPerson: {
        type: "",
        reason: "",
        name: "",
        surename: "",
        birtday: "",
        gender: "",
        email: "",
        phone: "",
        street: "",
        plz: "",
        place: "",

      },
      morePerson: [],
      extraPersonsCounter: 3
    }
  },

  methods: {
    addPersonForm() {

      let newPerson = {
        name: "",
        surename: "",
        birtday: "",
        gender: "",
      }

      this.morePerson.push(newPerson);
    }
  }
}
</script>

<template>

  <FormHeader/>

  <div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em] ">
    <PeronenAuswahl v-model.modelValue="mainPerson.type"/>
    <GrundAuwahl v-model.modelValue="mainPerson.reason"/>

    <div class="flex gap-[1.5em]">
      <ReworkedBSCInput v-model.modelValue="mainPerson.name" header-field="Vorname" input-type="text"></ReworkedBSCInput>
      <ReworkedBSCInput v-model.modelValue="mainPerson.surename" header-field="Nachname" input-type="text"></ReworkedBSCInput>

    </div>

    <div class="flex gap-[1.5em]">
      <ReworkedBSCInput v-model.modelValue="mainPerson.birtday" header-field="Geburtsdatum" input-type="date"></ReworkedBSCInput>
      <gender-selection v-model.modelValue="mainPerson.gender" class="flex-1 w-1/2"/>
    </div>

    <div class="flex gap-[1.5em] -sm:flex-col">
      <ReworkedBSCInput v-model.modelValue="mainPerson.email" header-field="Email" input-type="email"></ReworkedBSCInput>
      <ReworkedBSCInput v-model.modelValue="mainPerson.phone" header-field="Teleon/Mobil" input-type="text"></ReworkedBSCInput>

    </div>

    <div class="flex gap-[1.5em] -sm:flex-col">
      <ReworkedBSCInput v-model.modelValue="mainPerson.street" header-field="Straße" input-type="text"></ReworkedBSCInput>
      <ReworkedBSCInput v-model.modelValue="mainPerson.plz" header-field="PLZ" input-type="text"></ReworkedBSCInput>

    </div>

    <ReworkedBSCInput v-model.modelValue="mainPerson.place" header-field="Ort" input-type="text"></ReworkedBSCInput>


    <ExtraPersonForm v-model.extraModelValues="morePerson[index]" v-for="(person, index) in morePerson" :index="index"></ExtraPersonForm>


    <AddMemberButton @click="addPersonForm"/>

  </div>


</template>

<style scoped>

</style>