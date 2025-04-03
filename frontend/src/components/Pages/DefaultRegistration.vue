<script>
import CategoryPicker from "@/components/BasicRegistration/CategoryPicker.vue";
import DepartmentPicker from "@/components/BasicRegistration/DepartmentPicker.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import AddMemberButton from "@/components/BasicRegistration/AddMemberButton.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/BSCInput.vue";
import ExtraPersonForm from "@/components/BasicRegistration/ExtraPersonForm.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";
import NachweisFeld from "@/components/BasicRegistration/NachweisFeld.vue";

export default {

  name: "DefaultRegistration",
  components: {
    NachweisFeld,
    BSCInput: ReworkedBSCInput,
    ExtraPersonForm,
    AddMemberButton,
    GenderSelection,
    FormHeader,
    GrundAuwahl: DepartmentPicker,
    PeronenAuswahl: CategoryPicker
  },

  data() {
    return {
      mainPerson: {
        type: "",
        reason: "",
        name: "",
        surename: "",
        birthday: "",
        gender: "",
        email: "",
        phone: "",
        street: "",
        plz: "",
        place: "",
      },
      morePerson: [],
    }
  },

  methods: {
    useRegistrationStore,
    addPersonForm() {
      let newPerson = {
        name: "",
        surename: "",
        birthday: "",
        gender: "",
      }

      useRegistrationStore().registrationData.morePersons.push(newPerson);
    },
  },

  computed: {
    toggleNachweisActivation() {
      useRegistrationStore().isFilled.studentIdentification = !useRegistrationStore().registrationData.mainData.type === 'Schüler/Student über 18';
      return useRegistrationStore().registrationData.mainData.type === 'Schüler/Student über 18';
    },
  }

}
</script>

<template>

  <FormHeader header-text="Mitgliederregistrierung"/>

  <div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em] ">
    <PeronenAuswahl v-model.modelValue="useRegistrationStore().registrationData.mainData.type"
                    :is-not-valid="!useRegistrationStore().isFilled.defaultData.type && useRegistrationStore().triedToValidateBasicForm"
                    @change="useRegistrationStore().updateBasicValidation()"/>

    <NachweisFeld :is-active="toggleNachweisActivation"
                  :is-not-valid="!useRegistrationStore().isFilled.studentIdentification && useRegistrationStore().triedToValidateBasicForm"
                  @change="useRegistrationStore().updateBasicValidation()"
    />

    <GrundAuwahl v-model.modelValue="useRegistrationStore().registrationData.mainData.reason"
                 :is-not-valid="!useRegistrationStore().isFilled.defaultData.reason && useRegistrationStore().triedToValidateBasicForm"
                 @change="useRegistrationStore().updateBasicValidation()"/>

    <div class="flex gap-[1.5em]">
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.name"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.name && useRegistrationStore().triedToValidateBasicForm"
                header-field="Vorname"
                input-type="text"
                @input="useRegistrationStore().updateBasicValidation()"/>
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.surename"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.surename && useRegistrationStore().triedToValidateBasicForm"
                header-field="Nachname"
                input-type="text"
                @input="useRegistrationStore().updateBasicValidation()"/>
    </div>

    <div class="flex gap-[1.5em]">
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.birthday"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.birthday && useRegistrationStore().triedToValidateBasicForm"
                header-field="Geburtsdatum"
                input-type="date"
                @input="useRegistrationStore().updateBasicValidation()"/>
      <gender-selection v-model.modelValue="useRegistrationStore().registrationData.mainData.gender"
                        :is-not-valid="!useRegistrationStore().isFilled.defaultData.gender && useRegistrationStore().triedToValidateBasicForm"
                        class="flex-1 w-1/2"
                        @change="useRegistrationStore().updateBasicValidation()"/>
    </div>

    <div class="flex gap-[1.5em] max-[640px]:flex-col">
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.email"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.email && useRegistrationStore().triedToValidateBasicForm"
                header-field="Email"
                input-type="email"
                @input="useRegistrationStore().updateBasicValidation()"/>
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.phone"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.phone && useRegistrationStore().triedToValidateBasicForm"
                header-field="Telefon/Mobil"
                input-type="text"
                @input="useRegistrationStore().updateBasicValidation()"/>

    </div>

    <div class="flex gap-[1.5em] max-[640px]:flex-col">
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.street"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.street && useRegistrationStore().triedToValidateBasicForm"
                header-field="Straße"
                input-type="text"
                @input="useRegistrationStore().updateBasicValidation()"/>
      <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.plz"
                :is-not-valid="!useRegistrationStore().isFilled.defaultData.plz && useRegistrationStore().triedToValidateBasicForm"
                header-field="PLZ"
                input-type="text"
                @input="useRegistrationStore().updateBasicValidation()"/>

    </div>

    <BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.place"
              :is-not-valid="!useRegistrationStore().isFilled.defaultData.place && useRegistrationStore().triedToValidateBasicForm"
              header-field="Ort"
              input-type="text"
              @input="useRegistrationStore().updateBasicValidation()"/>

    <ExtraPersonForm v-for="(person, index) in useRegistrationStore().registrationData.morePersons"
                     v-model.extraModelValues="useRegistrationStore().registrationData.morePersons[index]"
                     :index="index"
                     :input-data="{name: person.name, surename: person.surename, birthday: person.birthday, gender: person.gender}"/>

    <AddMemberButton @click="addPersonForm"/>

  </div>

</template>

<style scoped>

</style>