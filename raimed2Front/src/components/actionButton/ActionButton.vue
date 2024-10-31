<template>
  <button
    class="btn dialog-action m-1 flex-nowrap"
    @click="handleClick"
    :disabled="disabled"
    :style="buttonStyle"
    @mouseover="hover = true"
    @mouseleave="hover = false"
  >
    <FontAwesomeIcon v-if="icon" :icon="icon" class="h-5 w-5" />
    <span>{{ label }}</span>
  </button>
</template>

<script lang="ts">
import { defineComponent, PropType, ref, computed } from "vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { IconDefinition } from "@fortawesome/fontawesome-svg-core";

export const Color = {
  Blue: "#9ccded",
  Orange: "#f1bf6d",
  Purple: "#d0b1dd",
  Green: "#2db784",
  Grey: "#d9d9d9"
} as const;

type ColorType = typeof Color[keyof typeof Color];

export default defineComponent({
  name: "ActionButton",
  components: { FontAwesomeIcon },
  props: {
    color: {
      type: String as PropType<ColorType>,
      required: true,
    },
    icon: {
      type: Object as PropType<IconDefinition>,
      required: false,
    },
    label: {
      type: String,
      required: true,
    },
    onClick: {
      type: Function as PropType<() => void>,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  setup(props) {
    const hover = ref(false);

    const buttonStyle = computed(() => {
      if (props.disabled) {
        switch (props.color) {
          case Color.Blue:
            return { backgroundColor: "#b7d8ee" };
          case Color.Orange:
            return { backgroundColor: "#eed0a1" };
          case Color.Purple:
            return { backgroundColor: "#d5c3dd" };
          case Color.Green:
            return { backgroundColor: "#8fd9b0" };
          case Color.Grey:
            return { backgroundColor: "#e6e6e6" };
        }
      } else if (hover.value) {
        switch (props.color) {
          case Color.Blue:
            return { backgroundColor: "#60aee2" };
          case Color.Orange:
            return { backgroundColor: "#eeb354" };
          case Color.Purple:
            return { backgroundColor: "#c694db" };
          case Color.Green:
            return { backgroundColor: "#1e8e5b" };
          case Color.Grey:
            return { backgroundColor: "#bfbfbf" };
        }
      }
      return { backgroundColor: props.color };
    });

    return {
      hover,
      buttonStyle,
    };
  },
  methods: {
    handleClick() {
      if (!this.disabled && this.onClick) this.onClick();
    },
  },
});
</script>

<style scoped>
.btn {
  transition: background-color 0.3s ease;
}
span {
  font-weight: 600;
}
</style>
