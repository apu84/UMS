module ums {
  export interface Filter {
    label: string;
    value: string;
    type: string;
    options?: FilterOption[];
  }

  export interface FilterOption {
    label: string;
    value: string | number | boolean;
  }

  export interface SelectedFilter {
    id: number;
    filter : FilterOption;
    value : FilterOption;
  }
}
