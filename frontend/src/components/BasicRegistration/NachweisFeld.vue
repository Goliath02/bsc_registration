<script>
import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore.ts";

export default {
  data() {
    return {
      isDragging: false,
      files: MemberRegistrationStore().registrationData.verificationFiles,
    };
  },

  props: {
    isActive: false,
    isNotValid: Boolean,
  },

  methods: {
    onChange() {
      if (
        this.$refs.file.files.length <= 2 &&
        this.files.length <= 2 &&
        !this.isInputFilesTooBig()
      ) {
        this.files.push(...this.$refs.file.files);
      }
    },

    isInputFilesTooBig() {
      for (const file in this.$refs.file.files) {
        if (this.checkFileSize(file)) {
          return true;
        }
      }

      for (const file in this.files) {
        if (this.checkFileSize(file)) {
          return true;
        }
      }

      return false;
    },

    checkFileSize(file) {
      //File is over 8MB big
      return file.size <= 8 * 1024 * 1024; // 8 MB
    },

    dragover(e) {
      e.preventDefault();
      this.isDragging = true;
    },
    dragleave() {
      this.isDragging = false;
    },
    drop(e) {
      e.preventDefault();
      this.$refs.file.files = e.dataTransfer.files;
      this.onChange();
      this.isDragging = false;
    },

    remove(i) {
      this.files.splice(i, 1);
    },

    generateURL(file) {
      let fileSrc = URL.createObjectURL(file);
      setTimeout(() => {
        URL.revokeObjectURL(fileSrc);
      }, 1000);
      return fileSrc;
    },
  },
};
</script>

<template>
  <div v-if="isActive" class="flex justify-center items-center text-center">
    <div
      :class="isNotValid ? 'border-2 border-red-700' : ''"
      class="flex flex-col justify-center items-center w-full h-[10em] border-2 border-[#585858] border-dashed rounded-lg overflow-hidden"
      @dragleave="dragleave"
      @dragover="dragover"
      @drop="drop"
    >
      <FormField
        name="file"
        label="File"
        v-slot="{ field, errorMessage }"
        class="w-full"
      >
        <input
          id="fileInput"
          ref="file"
          accept=".pdf,.jpg,.jpeg,.png"
          class="hidden-input"
          multiple
          name="file"
          type="file"
          @change="onChange"
        />
        <p v-if="errorMessage" class="text-red-600">{{ errorMessage }}</p>
      </FormField>

      <label class="file-label" for="fileInput">
        <div v-if="isDragging">Dateien hier loslassen.</div>
        <div v-else>
          Schüler-nachweise hier ablegen oder <u>hier klicken</u> zum hochladen.
        </div>
      </label>

      <div v-if="files.length" class="preview-container !border-[#585858]">
        <div
          v-for="file in files"
          :key="file.name"
          class="preview-card rounded-lg"
        >
          <div>
            <img :src="generateURL(file)" class="preview-img" />
            <p>
              {{ file.name }}
            </p>
          </div>
          <div>
            <button
              class="p-4 h-full bg-[#585858] rounded-lg"
              title="Remove file"
              type="button"
              @click="remove(files.indexOf(file))"
            >
              <b>×</b>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.hidden-input {
  opacity: 0;
  overflow: hidden;
  position: absolute;
  width: 1px;
  height: 1px;
}

.file-label {
  font-size: 20px;
  display: block;
  cursor: pointer;
}

.preview-container {
  display: flex;
}

.preview-card {
  display: flex;
  border: 1px solid #a2a2a2;
}

.preview-img {
  width: 50px;
  height: 50px;
  border-radius: 5px;
  border: 1px solid #a2a2a2;
  background-color: #a2a2a2;
}
</style>
