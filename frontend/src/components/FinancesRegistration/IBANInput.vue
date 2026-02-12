<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore";

const IBANSection1 = ref("");
const IBANSection2 = ref("");
const IBANSection3 = ref("");
const IBANSection4 = ref("");
const IBANSection5 = ref("");
const IBANSection6 = ref("");

onMounted(() => {
  setIbanIfAlreadyTyped();
});

const setIbanIfAlreadyTyped = () => {
  const currentInputIban =
    MemberRegistrationStore().registrationData.financialData.iban;

  if (currentInputIban) {
    let slicedIban = [];

    for (let i = 0; slicedIban.length !== 6; i += 4) {
      slicedIban.push(currentInputIban.slice(i, i + 4));
    }

    IBANSection1.value = slicedIban[0];
    IBANSection2.value = slicedIban[1];
    IBANSection3.value = slicedIban[2];
    IBANSection4.value = slicedIban[3];
    IBANSection5.value = slicedIban[4];
    IBANSection6.value = slicedIban[5];
  }
};

setIbanIfAlreadyTyped();

const fixPostInputSection = () => {
  IBANSection1.value = IBANSection1.value.substring(0, 4);
};

const getIBAN = () => {
  return [
    IBANSection1.value,
    IBANSection2.value,
    IBANSection3.value,
    IBANSection4.value,
    IBANSection5.value,
    IBANSection6.value,
  ].join("");
};

const onIbanInput = (event: any, max : number) => {
  postInput(event.data);
  if (event.target.value && event.target.value.length < max) {
  } else {
    event.target.value = event.target.value.substring(0, max);
    focusNext(event, max);
  }
  fixPostInputSection();
  MemberRegistrationStore().registrationData.financialData.iban = getIBAN();
};

const postInput = (input : string) => {
  if (input != null && input.length > 4) {
    let slices = [];

    for (let i = 0; slices.length !== 6; i += 4) {
      slices.push(input.slice(i, i + 4));
    }

    slices[5] = slices[5].slice(0, 2);

    switch (slices.length) {
      case 6:
        IBANSection6.value = slices[5];
      case 5:
        IBANSection5.value = slices[4];
      case 4:
        IBANSection4.value = slices[3];
      case 3:
        IBANSection3.value = slices[2];
      case 2:
        IBANSection2.value = slices[1];
      case 1:
        IBANSection1.value = slices[0];
        break;
    }
  }
};

const input1 = ref<HTMLInputElement | null>(null);
const input2 = ref<HTMLInputElement | null>(null);
const input3 = ref<HTMLInputElement | null>(null);
const input4 = ref<HTMLInputElement | null>(null);
const input5 = ref<HTMLInputElement | null>(null);
const input6 = ref<HTMLInputElement | null>(null);

const inputRefs = [input1, input2, input3, input4, input5, input6];

const focusNext = (event: Event, max: number) => {
  const target = event.target as HTMLInputElement;
  const index = Number(target.dataset.index); // kommt aus deinem template (data-index="1")

  if (target.value.length >= max) {
    const next = inputRefs[index]; // weil data-index 1-basiert ist

    if (next?.value) {
      next.value.focus();
    }
  }
};

const props = defineProps<{
  modelValue?: string;
  name?: string;
}>();

const emit = defineEmits<{ "update:modelValue": [string] }>();

const updateIBAN = () => {
  const iban = [
    IBANSection1.value,
    IBANSection2.value,
    IBANSection3.value,
    IBANSection4.value,
    IBANSection5.value,
    IBANSection6.value,
  ].join("");
  emit("update:modelValue", iban);
  MemberRegistrationStore().registrationData.financialData.iban = iban;
};

// whenever sections change → IBAN updaten
watch(
  [
    IBANSection1,
    IBANSection2,
    IBANSection3,
    IBANSection4,
    IBANSection5,
    IBANSection6,
  ],
  updateIBAN,
);
</script>

<template>
  <div class="font-bold text-[1.2em]">IBAN</div>
  <div class="flex gap-[1em] max-[1024px]:gap-[0.5em]">
    <input
      :ref="inputRefs[0]"
      v-model="IBANSection1"
      class="w-full flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="1"
      @input="onIbanInput($event, 4)"
    />
    <input
      :ref="inputRefs[1]"
      v-model="IBANSection2"
      class="w-full flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="2"
      maxlength="4"
      @input="onIbanInput($event, 4)"
    />
    <input
      :ref="inputRefs[2]"
      v-model="IBANSection3"
      class="w-full flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="3"
      maxlength="4"
      @input="onIbanInput($event, 4)"
    />
    <input
      :ref="inputRefs[3]"
      v-model="IBANSection4"
      class="w-full flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="4"
      maxlength="4"
      @input="onIbanInput($event, 4)"
    />
    <input
      :ref="inputRefs[4]"
      v-model="IBANSection5"
      class="w-full flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="5"
      maxlength="4"
      @input="onIbanInput($event, 4)"
    />

    <input
      :ref="inputRefs[5]"
      v-model="IBANSection6"
      class="w-1/2 flex-2 h-[3em] lg:px-[0.5em] bg-[#09090b] border border-[#71717a] rounded-lg font-medium appearance-none text-center"
      data-index="6"
      maxlength="2"
      @input="onIbanInput($event, 2)"
    />
    <FormField name="iban" label="IBAN">
      <input
        type="hidden"
        name="iban"
        :value="MemberRegistrationStore().registrationData.financialData.iban"
      />
    </FormField>
  </div>
</template>

<style scoped></style>
