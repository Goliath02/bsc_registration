<script setup>
import {RegistrationType} from "@/components/BasicRegistration/dto/RegistrationType.js";
import {onMounted, ref} from "vue";
import Popover from "primevue/popover";
import Button from "primevue/button";
import axios from "axios";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";

const modelValue = defineModel();
const props = defineProps({
  isNotValid: Boolean
})

const op = ref();
const prices = ref();

const toggle = (event) => {
  op.value.toggle(event);
}

onMounted(() => {

  axios.get(useRegistrationStore().getTargetURL() + "/priceList").then((res) => {
    prices.value = res.data;
  }).catch((err) => {
  })
})

</script>

<template>
  <div>

    <div class="w-full">
      <div class="font-bold text-[1.2em] flex  content-center text-center!">
        <div class="flex content-center justify-center">
        <h2 class="h-min">Kategorie</h2>
        </div>

        <div>
          <Button variant="text" rounded size="small" severity="contrast"  @click="toggle"  icon="pi pi-question-circle"/>
          <Popover ref="op">
            <div>
              <div v-for="price of prices">
                {{price}}
              </div>
            </div>
          </Popover>
        </div>
      </div>
      <select v-model="modelValue" :class="props.isNotValid ? 'border-2 border-red-700' : '' "
              class="w-full h-[3em] px-[1em] bg-[#585858] rounded-lg font-medium align-middle"
              value="Bitte auswählen">
	      <option v-for="type of RegistrationType">
		      {{type}}
	      </option>
      </select>
    </div>
  </div>
</template>

<style scoped>

</style>