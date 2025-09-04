<script setup lang="ts">
import { apiClient } from "@/apiClient";
import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { useRoute } from "vue-router";
import router from "@/router.js";

const open = defineModel<boolean>({ required: true });
const route = useRoute();
const courseId = route.params.id;

const queryClient = useQueryClient();


const { mutate: deleteCourse, isPending, isSuccess, isError: isSaveCourseError } = useMutation({
  mutationFn: () =>
    apiClient.delete("/api/course/delete/" + courseId),
  onSuccess: () => {
    open.value = false;
    router.push("/courses")

  }
});

const submitDelete = () => {
  deleteCourse();

}

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
    <div
      class="flex flex-col gap-4 justify-center items-center w-full">

      <h1 class="text-4xl font-bold">Diesen Kurs löschen?</h1>

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
            animationDuration="1.5s" />
          <p v-else @click="submitDelete">Löschen</p>
        </Button>
      </div>

    </div>
  </Dialog>
</template>

<style scoped></style>
