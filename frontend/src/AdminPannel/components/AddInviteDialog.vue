<script setup lang="ts">

import {Form} from "@primevue/forms";
import {ref} from "vue";
import {yupResolver} from "@primevue/forms/resolvers/yup";
import * as yup from "yup";
import {useMutation, useQueryClient} from "@tanstack/vue-query";
import {apiClient} from "@/apiClient.js";

const open = defineModel<boolean>({required: true});

const createInviteDto = ref({
  email: "",
})

const resolver = yupResolver(
    yup.object().shape({
      email: yup.string().email().required()
    }),
);

const queryClient = useQueryClient();

const {mutate: sendInvite, isPending, isSuccess, isError: isSavePlaceError} = useMutation({
  mutationFn: () =>
      apiClient.post("/api/invites/member", {
        email: createInviteDto.value.email,
        type: "COURSE_OWNER",
      }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["invites"]});
    open.value = false;
  }
});

const onFormSubmit = (values) => {
  if (values.valid) {
    sendInvite();
  }
};

</script>

<template>
  <Dialog
      v-model:visible="open"
      modal
      :closable="false"
      close-on-escape
      :draggable="false"
      :show-header="false"
      class="p-4"
  >
    <Form
        v-slot="$form"
        :initialValues="createInviteDto"
        id="defaultRegistrationForm"
        :resolver="resolver"
        @submit="onFormSubmit"
        class="flex flex-col gap-4 justify-center items-center w-full">

      <h1 class="text-4xl font-bold">Einladung erstellen</h1>

      <InputText
          v-model="createInviteDto.email"
          placeholder="Email"
          class="w-full"
          name="email"
      />

      <div class="flex gap-4 w-full justify-center items-center">
        <Button @click="open = false" class="w-24 h-10 px-4 py-2">Zurück</Button>
        <Button type="submit" class="w-24 h-10 px-4 py-2">
          <ProgressSpinner
              v-if="isPending"
              style="
            width: 1em;
            height: 1em;
            --p-progressspinner-color-one: #fff;
            --p-progressspinner-color-two: #fff;
            --p-progressspinner-color-three: #fff;
            --p-progressspinner-color-four: #fff;
          "
              strokeWidth="8"
              animationDuration="1.5s"/>
          <p v-else>Senden</p>
        </Button>
      </div>
    </Form>
  </Dialog>

</template>

<style scoped>

</style>